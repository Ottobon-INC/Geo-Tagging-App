package com.orcalabs.hrms.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.AttendanceStatus
import com.orcalabs.hrms.data.model.LeaveStatus
import com.orcalabs.hrms.data.model.TaskPriority
import com.orcalabs.hrms.data.model.TaskStatus
import com.orcalabs.hrms.data.model.UserRole
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.IndigoSoft
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusAbsent
import com.orcalabs.hrms.ui.theme.StatusAbsentSoft
import com.orcalabs.hrms.ui.theme.StatusApproved
import com.orcalabs.hrms.ui.theme.StatusFieldDuty
import com.orcalabs.hrms.ui.theme.StatusFieldDutySoft
import com.orcalabs.hrms.ui.theme.StatusHalfDay
import com.orcalabs.hrms.ui.theme.StatusHalfDaySoft
import com.orcalabs.hrms.ui.theme.StatusHoliday
import com.orcalabs.hrms.ui.theme.StatusHolidaySoft
import com.orcalabs.hrms.ui.theme.StatusPending
import com.orcalabs.hrms.ui.theme.StatusPendingSoft
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.StatusPresentSoft
import com.orcalabs.hrms.ui.theme.StatusRejected
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun StatusBadge(
    label: String,
    textColor: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AttendanceBadge(status: AttendanceStatus, modifier: Modifier = Modifier) {
    val (text, bg) = when (status) {
        AttendanceStatus.PRESENT -> Pair(StatusPresent, StatusPresentSoft)
        AttendanceStatus.ABSENT -> Pair(StatusAbsent, StatusAbsentSoft)
        AttendanceStatus.HALF_DAY -> Pair(StatusHalfDay, StatusHalfDaySoft)
        AttendanceStatus.ON_LEAVE -> Pair(StatusPending, StatusPendingSoft)
        AttendanceStatus.WEEK_OFF -> Pair(SlateTextSecondary, SlateBorder)
        AttendanceStatus.HOLIDAY -> Pair(StatusHoliday, StatusHolidaySoft)
        AttendanceStatus.FIELD_DUTY -> Pair(StatusFieldDuty, StatusFieldDutySoft)
    }
    StatusBadge(label = status.label, textColor = text, bgColor = bg, modifier = modifier)
}

@Composable
fun LeaveBadge(status: LeaveStatus, modifier: Modifier = Modifier) {
    val (text, bg) = when (status) {
        LeaveStatus.PENDING -> Pair(StatusPending, StatusPendingSoft)
        LeaveStatus.APPROVED -> Pair(StatusApproved, StatusPresentSoft)
        LeaveStatus.REJECTED -> Pair(StatusRejected, StatusAbsentSoft)
        LeaveStatus.CANCELLED -> Pair(SlateTextSecondary, SlateBorder)
    }
    StatusBadge(label = status.label, textColor = text, bgColor = bg, modifier = modifier)
}

@Composable
fun TaskBadge(status: TaskStatus, modifier: Modifier = Modifier) {
    val (text, bg) = when (status) {
        TaskStatus.PENDING -> Pair(SlateTextSecondary, SlateBorder)
        TaskStatus.IN_PROGRESS -> Pair(StatusFieldDuty, StatusFieldDutySoft)
        TaskStatus.COMPLETED, TaskStatus.APPROVED -> Pair(StatusApproved, StatusPresentSoft)
        TaskStatus.SUBMITTED_FOR_REVIEW -> Pair(StatusPending, StatusPendingSoft)
        TaskStatus.OVERDUE -> Pair(StatusAbsent, StatusAbsentSoft)
    }
    StatusBadge(label = status.label, textColor = text, bgColor = bg, modifier = modifier)
}

@Composable
fun PriorityBadge(priority: TaskPriority, modifier: Modifier = Modifier) {
    val (text, bg) = when (priority) {
        TaskPriority.LOW -> Pair(SlateTextSecondary, SlateBorder)
        TaskPriority.MEDIUM -> Pair(StatusFieldDuty, StatusFieldDutySoft)
        TaskPriority.HIGH -> Pair(StatusPending, StatusPendingSoft)
        TaskPriority.URGENT -> Pair(StatusAbsent, StatusAbsentSoft)
    }
    StatusBadge(label = priority.label, textColor = text, bgColor = bg, modifier = modifier)
}

@Composable
fun RoleBadge(role: UserRole, modifier: Modifier = Modifier) {
    val (text, bg) = when (role) {
        UserRole.BE -> Pair(StatusFieldDuty, StatusFieldDutySoft)
        UserRole.RSM -> Pair(TealPrimary, TealSoft)
        UserRole.ZSM -> Pair(IndigoAccent, IndigoSoft)
        UserRole.GM, UserRole.ADMIN, UserRole.HR -> Pair(Color(0xFF7C3AED), Color(0xFFEDE9FE))
    }
    StatusBadge(label = role.name, textColor = text, bgColor = bg, modifier = modifier)
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    accentColor: Color = TealPrimary,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateTextSecondary
                )
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(accentColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = SlateDark,
                fontWeight = FontWeight.Bold
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateTextSecondary
                )
            }
        }
    }
}

@Composable
fun EmployeeAvatar(
    name: String,
    sizeDp: Int = 40,
    backgroundColor: Color = TealPrimary,
    modifier: Modifier = Modifier
) {
    val initials = name.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (sizeDp * 0.38).sp
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = SlateDark
        )
        if (actionText != null && onActionClick != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onActionClick() }
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelMedium,
                    color = TealPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = TealPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun OrcaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    isOutlined: Boolean = false
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier.height(48.dp)
        ) {
            if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text, fontWeight = FontWeight.SemiBold)
        }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TealPrimary,
                contentColor = Color.White
            ),
            modifier = modifier.height(48.dp)
        ) {
            if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text, fontWeight = FontWeight.SemiBold)
        }
    }
}
