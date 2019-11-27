import { SecurityState } from './system/securityState';
import { GroupSearchResult } from './userInterface/groupSearchResult';
import { GroupSearchFormFields } from './userInterface/groupSearchFormFields';

export interface StoreState {

    system: SecurityState;
    groupSearchResults: {
        groups: GroupSearchResult[],
        page: number
    };
    groupSearchFormFields: GroupSearchFormFields;
    size: number;

}