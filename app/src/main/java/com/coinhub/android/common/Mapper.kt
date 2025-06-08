package com.coinhub.android.common

import com.coinhub.android.data.dtos.response.AiChatResponseDto
import com.coinhub.android.data.dtos.response.AvailablePlanResponseDto
import com.coinhub.android.data.dtos.response.CreateTopUpResponseDto
import com.coinhub.android.data.dtos.response.DeviceResponseDto
import com.coinhub.android.data.dtos.response.NotificationResponseDto
import com.coinhub.android.data.dtos.response.PlanResponseDto
import com.coinhub.android.data.dtos.response.SourceResponseDto
import com.coinhub.android.data.dtos.response.TicketHistoryResponseDto
import com.coinhub.android.data.dtos.response.TicketResponseDto
import com.coinhub.android.data.dtos.response.TopUpResponseDto
import com.coinhub.android.data.dtos.response.UserResponseDto
import com.coinhub.android.domain.models.AiChatModel
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.CreateTopUpModel
import com.coinhub.android.domain.models.DeviceModel
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.domain.models.PlanModel
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketHistoryModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.domain.models.UserModel
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun AvailablePlanResponseDto.toAvailablePlanModel() = AvailablePlanModel(
    planId = this.planId.toInt(),
    planHistoryId = this.planHistoryId.toInt(),
    days = this.days,
    rate = this.rate
)

fun PlanResponseDto.toPlanModel() = PlanModel(
    id = this.id,
    days = this.days
)

fun SourceResponseDto.toSourceModel() = SourceModel(
    id = this.id,
    balance = BigInteger(this.balance)
)

fun TicketHistoryResponseDto.toTicketHistoryModel() = TicketHistoryModel(
    issuedAt = LocalDate.parse(this.issuedAt),
    maturedAt = LocalDate.parse(this.maturedAt),
    principal = BigInteger(this.principal),
    interest = BigInteger(this.interest)
)

fun TicketResponseDto.toTicketModel() = TicketModel(
    id = this.id,
    openedAt = ZonedDateTime.parse(this.openedAt).toLocalDate(),
    closedAt = this.closedAt?.let { ZonedDateTime.parse(it).toLocalDate() },
    status = this.status,
    method = this.method,
    ticketHistories = this.ticketHistories.map { it.toTicketHistoryModel() },
    plan = this.plan.toPlanModel()
)

@OptIn(ExperimentalUuidApi::class)
fun UserResponseDto.toUserModel() = UserModel(
    id = Uuid.parse(this.id),
    fullName = this.fullName,
    citizenId = this.citizenId,
    birthDate = ZonedDateTime.parse(birthDate).toLocalDate(),
    address = address,
    createdAt = ZonedDateTime.parse(createdAt),
    deletedAt = this.deletedAt?.let { ZonedDateTime.parse(it) },
    avatar = this.avatar
)

@OptIn(ExperimentalUuidApi::class)
fun TopUpResponseDto.toTopUpModel() = TopUpModel(
    id = Uuid.parse(this.id),
    createdAt = ZonedDateTime.parse(this.createdAt),
    amount = BigInteger(this.amount),
    status = this.status.toTopUpStatusEnum(),
    provider = this.provider.toTopUpProviderEnum()
)

fun String.toTopUpProviderEnum(): TopUpModel.ProviderEnum = when (this.lowercase()) {
    "vnpay" -> TopUpModel.ProviderEnum.vnpay
    "momo" -> TopUpModel.ProviderEnum.momo
    "zalo" -> TopUpModel.ProviderEnum.zalo
    else -> throw IllegalArgumentException("Unknown provider: $this")
}

fun String.toTopUpStatusEnum(): TopUpModel.StatusEnum = when (this.lowercase()) {
    "processing" -> TopUpModel.StatusEnum.proccesing
    "success" -> TopUpModel.StatusEnum.success
    "declined" -> TopUpModel.StatusEnum.declined
    "overdue" -> TopUpModel.StatusEnum.overdue
    else -> throw IllegalArgumentException("Unknown status: $this")
}

@OptIn(ExperimentalUuidApi::class)
fun CreateTopUpResponseDto.toCreateTopUpModelResponse() = CreateTopUpModel(
    url = this.url,
    topUpId = Uuid.parse(this.topUpId)
)

fun DeviceResponseDto.toDeviceModel() = DeviceModel(
    id = this.id,
    fcmToken = this.fcmToken
)

@OptIn(ExperimentalUuidApi::class)
fun NotificationResponseDto.toNotificationModel() = NotificationModel(
    id = Uuid.parse(this.id),
    createdAt = ZonedDateTime.parse(this.createdAt),
    title = this.title,
    body = this.body,
    isRead = this.isRead
)

fun String.toAiChatRoleEnum(): AiChatModel.Role = when (this.lowercase()) {
    "user" -> AiChatModel.Role.USER
    "assistant" -> AiChatModel.Role.ASSISTANT
    "system" -> AiChatModel.Role.SYSTEM
    else -> throw IllegalArgumentException("Unknown role: $this")
}

fun AiChatResponseDto.toAiChatModel() = AiChatModel(
    message = this.message,
    role = this.role.toAiChatRoleEnum(),
)