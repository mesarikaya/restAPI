export interface GroupSearchResult {
    name: string,
    groupDetails: {
        originCity: string,
        originZipcode: string,
        originRange: number,
        destinationCity: string,
        destinationZipCode: string,
        destinationRange: number
    },
    members: {userNames: string[]}
}