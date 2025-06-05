package com.coinhub.android.common

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
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.models.DeviceModel
import com.coinhub.android.data.models.NotificationModel
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.data.models.UserModel
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun AvailablePlanResponseDto.toAvailablePlanModel() = AvailablePlanModel(
    planId = this.planId,
    planHistoryId = this.planHistoryId,
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
    openedAt = LocalDate.parse(this.openedAt),
    closedAt = this.closedAt?.let { LocalDate.parse(it) },
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
    birthDate = LocalDate.parse(birthDate),
    address = address,
    createdAt = LocalDate.parse(createdAt),
    deletedAt = this.deletedAt?.let { LocalDate.parse(it) },
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