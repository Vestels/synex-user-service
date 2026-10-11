---
"synex-user-service": patch
---

- Initial bootstrap including:
    - Added `AccountLinkingService` for Auth0 account linking and unlinking
    - Fixed Auth0 Management API integration:
        - Corrected linked account flows, including linking and unlinking identities
        - Replaced `UserExists` with `GetUser` to retrieve and map user identities
        - Added a secondary JWT validator for account linking
    - Refactored DTOs for improved consistency
    - Simplified business logic and service workflows
    - Fixed identity provider mapping during login tracking
    - Improved the Changesets release workflow to prevent releases without a version bump
    - Updated Maven version management for stable releases and development snapshots
