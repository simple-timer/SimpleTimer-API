package dev.simpletimer.simpletimer_api.database.dummy

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * JDAのGuildMessageChannelの模倣
 *
 * @property channelId
 */
class GuildMessageChannel(val channelId: Long)

/**
 * [GuildMessageChannel]のシリアライザー
 */
object GuildMessageChannelSerializer : KSerializer<GuildMessageChannel> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GuildMessageChannel", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: GuildMessageChannel) {
        //idを取得
        val idLong = value.channelId
        //エンコード
        encoder.encodeLong(idLong)
    }

    override fun deserialize(decoder: Decoder): GuildMessageChannel {
        return GuildMessageChannel(decoder.decodeLong())
    }
}