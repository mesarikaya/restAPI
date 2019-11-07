import { GroupSearchResult } from '../types/userInterface/groupSearchResult';
import { GroupSearchActionType, SEARCH_GROUP_REQUEST } from '../types/action/groupSearchActionType';

// Set initial state
const initialState: {groups: GroupSearchResult[], page: number} = {
    groups: [],
    page: 0
};
export function groupSearchReducer(
    state = initialState,
    action: GroupSearchActionType
    ): {groups:GroupSearchResult[], page:number} {

    switch (action.type) {

        case SEARCH_GROUP_REQUEST:

            // tslint:disable-next-line:no-console
            console.log('Inside SEARCH_GROUP_REQUEST REDUCER, PAYLOAD IS: ', action.payload);

            return Object.assign({}, 
                                 state, 
                                 {
                                    groups: {...action.payload.groups},
                                    page: action.payload.page
                                 });
        default:
            return state;
    }
};