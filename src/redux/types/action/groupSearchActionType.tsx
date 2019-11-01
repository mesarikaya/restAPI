import { GroupSearchResult } from '../userInterface/groupSearchResult';

export const SEARCH_GROUP_REQUEST = 'SEARCH_GROUP_REQUEST';

interface SearchGroupRequest {
    type: typeof SEARCH_GROUP_REQUEST
    payload: GroupSearchResult[]
}

export type GroupSearchActionType = SearchGroupRequest;