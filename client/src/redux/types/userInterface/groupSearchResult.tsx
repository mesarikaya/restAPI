import { GroupUser } from './groupUser';

export interface GroupSearchResult {
    id: string,
    name: string,
    groupDetails: {
        originCity: string,
        originZipcode: string,
        originRange: number,
        destinationCity: string,
        destinationZipCode: string,
        destinationRange: number
    },
    members: {
        users: GroupUser[]
    }
}