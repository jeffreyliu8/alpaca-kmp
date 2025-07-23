package com.jeffreyliu.alpaca.model

import kotlinx.serialization.Serializable

@Serializable
enum class AccountStatus {
    ONBOARDING,         // The account is onboarding.
    SUBMISSION_FAILED,  // The account application submission failed for some reason.
    SUBMITTED,          // The account application has been submitted for review.
    ACCOUNT_UPDATED,    // The account information is being updated.
    APPROVAL_PENDING,   // The final account approval is pending.
    ACTIVE,             // The account is active for trading.
    REJECTED,           // The account application has been rejected.
}
