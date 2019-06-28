import { GroupArray } from '../userInterface/GroupArray';

export const SEARCH_GROUP_REQUEST = 'SEARCH_GROUP_REQUEST';

interface SearchGroupRequest {
    type: typeof SEARCH_GROUP_REQUEST
    payload: GroupArray
}

export type GroupSearchActionTypes = SearchGroupRequest;