package net.smok.villagerebalance;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOfferList;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.utility.TradeRegistries;

import java.util.Collection;

public class MerchantCommand implements CommandRegistrationCallback {


    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType FAILED_UUID_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.invalidPosition"));
    private static final SimpleCommandExceptionType INVALID_ENTITY_TYPE = new SimpleCommandExceptionType(Text.literal("Entity must be villager."));

    public static final SuggestionProvider<ServerCommandSource> TRADE_OFFERS =
            SuggestionProviders.register(Identifier.of(Values.MOD_ID, "trade_offers"), (context, builder) ->
                    CommandSource.suggestIdentifiers(TradeRegistries.tradeOffers.offers().keySet(), builder));

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("merchant").requires(source -> source.hasPermissionLevel(2))
                //.then(CommandManager.literal("summon").then(CommandManager.argument("position", Vec3ArgumentType.vec3()).executes(this::summon)))
                .then(CommandManager.literal("edit").then(CommandManager.argument("entity", EntityArgumentType.entities())
                        .then(CommandManager.literal("remove").then(CommandManager.argument("index", IntegerArgumentType.integer(0)).executes(this::remove)))
                        .then(CommandManager.literal("addId").then(CommandManager.argument("id", IdentifierArgumentType.identifier()).suggests(TRADE_OFFERS).executes(this::addId)))/*
                        .then(CommandManager.literal("addOffer")
                                .then(CommandManager.argument("firstBuy", ItemStackArgumentType.itemStack(access))
                                        .then(CommandManager.argument("secondBuy", ItemStackArgumentType.itemStack(access))
                                                .then(CommandManager.argument("sell", ItemStackArgumentType.itemStack(access))
                                                        .then(CommandManager.argument("maxUses", IntegerArgumentType.integer())
                                                                .then(CommandManager.argument("reward", IntegerArgumentType.integer(0, 1))
                                                                        .then(CommandManager.argument("exp", IntegerArgumentType.integer(0))
                                                                                .then(CommandManager.argument("priceMultiplier", FloatArgumentType.floatArg(0, 1))
                                                                                        .executes(this::addOffer)
                                                                                ))))))))*/
                ))
        );

    }
/*
    private int addOffer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entity");
        Identifier id = IdentifierArgumentType.getIdentifier(context, "id");


        return 0;
    }*/

    private int addId(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entity");
        Identifier id = IdentifierArgumentType.getIdentifier(context, "id");

        OfferFactory offerFactory = TradeRegistries.tradeOffers.get(id);
        if (offerFactory == null) throw new SimpleCommandExceptionType(Text.literal("Unknown Trade Factory ID.")).create();

        if (entities.stream().anyMatch(entity -> !(entity instanceof MerchantEntity))) {
            throw INVALID_ENTITY_TYPE.create();
        }

        for (Entity entity : entities) {
            if (!(entity instanceof MerchantEntity merchantEntity)) continue;
            TradeOfferList offers = merchantEntity.getOffers();

            offers.add(offerFactory.create(entity, ((MerchantEntity) entity).getRandom()));
            context.getSource().sendFeedback(() -> Text.literal("Added " + id + " for " + entity.getDisplayName().getString()), true);
        }
        return 1;
    }

    private int remove(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entity");

        if (entities.stream().anyMatch(entity -> !(entity instanceof MerchantEntity))) {
            throw INVALID_ENTITY_TYPE.create();
        }

        for (Entity entity : entities) {
            if (!(entity instanceof MerchantEntity)) continue;
            int index = context.getArgument("index", Integer.class);

            TradeOfferList offers = ((MerchantEntity) entity).getOffers();
            int size = offers.size();
            if (index >= size) throw new SimpleCommandExceptionType(Text.literal("Index must be less then size of offers list [" + size + ']')).create();

            offers.remove(index);
            context.getSource().sendFeedback(() -> Text.literal("Removed " + index + " offer for " + entity.getDisplayName().getString()), true);
        }
        return 1;
    }
/*
    private int summon(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Vec3d pos = Vec3ArgumentType.getVec3(context, "position");
        BlockPos blockPos = BlockPos.ofFloored(pos);




        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putString("id", "minecraft:villager");
            ServerWorld serverWorld = context.getSource().getWorld();
            MerchantEntity entity = (MerchantEntity) EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            if (entity == null) {
                throw FAILED_EXCEPTION.create();
            } else {

                ((MobEntity)entity).initialize(serverWorld, serverWorld.getLocalDifficulty(entity.getBlockPos()), SpawnReason.COMMAND, (EntityData)null, (NbtCompound)null);


                if (!serverWorld.spawnNewEntityAndPassengers(entity)) {
                    throw FAILED_UUID_EXCEPTION.create();
                } else {
                    context.getSource().sendFeedback(() -> Text.translatable("commands.summon.success", entity.getDisplayName()), true);
                    return 1;
                }
            }
        }
    }*/
}
