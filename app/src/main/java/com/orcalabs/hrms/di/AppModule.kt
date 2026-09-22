package com.orcalabs.hrms.di

import com.orcalabs.hrms.data.repository.MockAttendanceRepository
import com.orcalabs.hrms.data.repository.MockAuthRepository
import com.orcalabs.hrms.data.repository.MockChatRepository
import com.orcalabs.hrms.data.repository.MockEmployeeRepository
import com.orcalabs.hrms.data.repository.MockFieldDutyRepository
import com.orcalabs.hrms.data.repository.MockLeaveRepository
import com.orcalabs.hrms.data.repository.MockOfficeRepository
import com.orcalabs.hrms.data.repository.MockTaskRepository
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.ChatRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import com.orcalabs.hrms.domain.repository.OfficeRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Single Switch Point for Multi-tenancy / Backend:
 * In Phase 1, all bindings point to MockRepositories.
 * In Phase 2, when Supabase keys and schemas are configured,
 * change the implementation classes here to SupabaseAuthRepository, etc.
 * The entire UI, Navigation, and ViewModels remain 100% untouched!
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: MockAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(impl: MockAttendanceRepository): AttendanceRepository

    @Binds
    @Singleton
    abstract fun bindLeaveRepository(impl: MockLeaveRepository): LeaveRepository

    @Binds
    @Singleton
    abstract fun bindFieldDutyRepository(impl: MockFieldDutyRepository): FieldDutyRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: MockTaskRepository): TaskRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(impl: MockChatRepository): ChatRepository

    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(impl: MockEmployeeRepository): EmployeeRepository

    @Binds
    @Singleton
    abstract fun bindOfficeRepository(impl: MockOfficeRepository): OfficeRepository
}
