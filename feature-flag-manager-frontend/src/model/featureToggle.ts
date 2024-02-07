export type FeatureToggle = {
    id?: string,
	displayName?: string,
	technicalName: string,
	expiresOn?: string,
	description?: string,
	inverted: boolean
	status?: FeatureToggleStatus,
	customerIds?: string[],
	createdAt?: string,
	modifiedAt?: string,
}

export enum FeatureToggleStatus {
    ACTIVE = "ACTIVE",
    ARCHIVED = "ARCHIVED"
}