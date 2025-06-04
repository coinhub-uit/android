package com.coinhub.android.common

import com.coinhub.android.data.dtos.response.AvailablePlanDto
import com.coinhub.android.data.dtos.response.CreateTopUpResponseDto
import com.coinhub.android.data.dtos.response.DeviceDto
import com.coinhub.android.data.dtos.response.PlanDto
import com.coinhub.android.data.dtos.response.SourceDto
import com.coinhub.android.data.dtos.response.TicketDto
import com.coinhub.android.data.dtos.response.TicketHistoryDto
import com.coinhub.android.data.dtos.response.TopUpDto
import com.coinhub.android.data.dtos.response.UserDto
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.models.DeviceModel
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.data.models.TopUpStatusEnum
import com.coinhub.android.data.models.UserModel
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun AvailablePlanDto.toAvailablePlanModel() = AvailablePlanModel(
    planId = this.planId,
    planHistoryId = this.planHistoryId,
    days = this.days,
    rate = this.rate
)

fun PlanDto.toPlanModel() = PlanModel(
    id = this.id,
    days = this.days
)

fun SourceDto.toSourceModel() = SourceModel(
    id = this.id,
    balance = BigInteger(this.balance)
)

fun TicketHistoryDto.toTicketHistoryModel() = TicketHistoryModel(
    issuedAt = LocalDate.parse(this.issuedAt),
    maturedAt = LocalDate.parse(this.maturedAt),
    principal = BigInteger(this.principal),
    interest = BigInteger(this.interest)
)

fun TicketDto.toTicketModel() = TicketModel(
    id = this.id,
    openedAt = LocalDate.parse(this.openedAt),
    closedAt = this.closedAt?.let { LocalDate.parse(it) },
    status = this.status,
    method = this.method,
    ticketHistories = this.ticketHistories.map { it.toTicketHistoryModel() },
    plan = this.plan.toPlanModel()
)

@OptIn(ExperimentalUuidApi::class)
fun UserDto.toUserModel() = UserModel(
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
fun TopUpDto.toTopUpModel() = TopUpModel(
    id = Uuid.parse(this.id),
    createdAt = ZonedDateTime.parse(this.createdAt),
    amount = BigInteger(this.amount),
    status = this.status.toTopUpStatusEnum(),
    provider = this.provider.toTopUpProviderEnum()
)

fun String.toTopUpProviderEnum(): TopUpProviderEnum = when (this.lowercase()) {
    "vnpay" -> TopUpProviderEnum.vnpay
    "momo" -> TopUpProviderEnum.momo
    "zalo" -> TopUpProviderEnum.zalo
    else -> throw IllegalArgumentException("Unknown provider: $this")
}

fun String.toTopUpStatusEnum(): TopUpStatusEnum = when (this.lowercase()) {
    "processing" -> TopUpStatusEnum.proccesing
    "success" -> TopUpStatusEnum.success
    "declined" -> TopUpStatusEnum.declined
    "overdue" -> TopUpStatusEnum.overdue
    else -> throw IllegalArgumentException("Unknown status: $this")
}

@OptIn(ExperimentalUuidApi::class)
fun CreateTopUpResponseDto.toCreateTopUpModelResponse() = CreateTopUpModel(
    url = this.url,
    topUpId = Uuid.parse(this.topUpId)
)

fun DeviceDto.toDeviceModel() = DeviceModel(
    id = this.id,
    fcmToken = this.fcmToken
)