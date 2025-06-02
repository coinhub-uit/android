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
import com.coinhub.android.data.models.UserModel
import kotlinx.datetime.LocalDate
import java.math.BigDecimal
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
    balance = BigDecimal(this.balance)
)

fun TicketHistoryDto.toTicketHistoryModel() = TicketHistoryModel(
    issuedAt = LocalDate.parse(this.issuedAt),
    maturedAt = this.maturedAt?.let { LocalDate.parse(it) },
    principal = BigDecimal(this.principal),
    interest = BigDecimal(this.interest)
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
    createdAt = LocalDate.parse(this.createdAt),
    amount = BigDecimal(this.amount),
    status = this.status,
    provider = this.provider
)

@OptIn(ExperimentalUuidApi::class)
fun CreateTopUpResponseDto.toCreateTopUpModelResponse() = CreateTopUpModel(
    url = this.url,
    topUpId = Uuid.parse(this.topUpId)
)

fun DeviceDto.toDeviceModel() = DeviceModel(
    id = this.id,
    fcmToken = this.fcmToken
)