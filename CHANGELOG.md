# synex-user-service

## 0.1.3

### Patch Changes

- [#1](https://github.com/Vestels/synex-user-service/pull/1) [`6cb3cf4`](https://github.com/Vestels/synex-user-service/commit/6cb3cf4cba2bf039e9fe8d4a531142a61c5015fb) - - Initial bootstrap including:
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

## 0.1.1

### Patch Changes

- [`714bb88`](https://github.com/Vestels/synex-user-service/commit/714bb88faeb0b75070e71524c155c6a0e4f4abc5) - Implemented CI flow -> commitlinting / husky hooks / changelog / auto release with Maven version bump
