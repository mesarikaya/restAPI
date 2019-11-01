import { GroupSearchResult } from '../types/userInterface/groupSearchResult';
import { GroupSearchActionType, SEARCH_GROUP_REQUEST } from '../types/action/groupSearchActionType';

// Set initial state
const initialState: GroupSearchResult[] = [];

export function groupSearchReducer(
    state = initialState,
    action: GroupSearchActionType
    ): GroupSearchResult[] {

    switch (action.type) {

        case SEARCH_GROUP_REQUEST:

            // tslint:disable-next-line:no-console
            console.log('Inside SEARCH_GROUP_REQUEST REDUCER, PAYLOAD IS: ', action.payload);

            return Object.assign({}, state,  {...action.payload});
        default:
            return state
    }
};