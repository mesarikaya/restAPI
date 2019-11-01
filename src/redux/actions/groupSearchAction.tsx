import axios from "axios";
axios.defaults.withCredentials = true; // to make use of jwt
import { Dispatch } from "redux";
import { GroupSearchActionType }from '../types/action/groupSearchActionType';
import { GroupSearchFormFields } from 'src/redux/types/userInterface/groupSearchFormFields';
import { GroupSearchResult } from '../types/userInterface/groupSearchResult';

// Set the API url for back end calls
const url = process.env.REACT_APP_NODE_ENV === 'production' ? "/api/v1/" : "http://localhost:8080/api/v1/";

/**
 * Make GET request and dipatch the image data to be shown via redux  
 * @param e HTML Form Event
 * @param formFields Login form input data
 */
export function SearchGroups(event: React.FormEvent<HTMLFormElement>, 
                             formFields: GroupSearchFormFields,
                             token: string) {
    
    if (event !== null) { 
        event.preventDefault(); 
    }
    
    // Set data to send with Post request
    const data = formFields;
    
    const params = new URLSearchParams();
    params.append('origin', data.origin);
    params.append('destination', data.destination);
    params.append('originRange', data.originRange.toString());
    params.append('destinationRange', data.destinationRange.toString());
    params.append('page', '0');
    params.append('size', '3');

    // tslint:disable-next-line:no-console
    console.log('REDUX SEARCH GROUP ACTION IS IN PROGRESS', 'Requesting: ', event);

    // tslint:disable-next-line:no-console
    console.log('environment is', process.env.NODE_ENV);
    
    return ((dispatch: Dispatch<GroupSearchActionType>) => {
        return (axios.get(`${url}groups`, {
            headers: {
                Authorization: "Bearer " + token,
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Cache: "no-cache"
            },
            params,
            withCredentials: true
        }).then((response) => {
            
            const initialState: GroupSearchResult[] = [];

            let payload= initialState;

            // tslint:disable-next-line:no-console
            console.log("SENDING TO THE REDUCER", response);

            // Depending on response status, allow or not for login
            if (response.status === 200) {
                payload= response.data;
                // tslint:disable-next-line:no-console
                console.log("SENDING TO THE REDUCER");
                dispatch({ type: 'SEARCH_GROUP_REQUEST', payload });
            }else {
                // TODO: CREATE ERROR HANDLERS
                // tslint:disable-next-line:no-console
                console.log("Error in axios");
                dispatch({ type: 'SEARCH_GROUP_REQUEST', payload});
            }
            // TODO: PUT THE RIGHT type for error inside the catch
        })
        .catch((error: any) => {
            // handle error
            // tslint:disable-next-line:no-console
            console.log("Error in get is:", error.response );
            throw (error);
        }));   
    });
};