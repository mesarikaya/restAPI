import { Dispatch } from "redux";
import { JwtAuthActionTypes }from '../../types/action/jwtAuthActionType';

// Set the API url for back end calls
// const url = process.env.NODE_ENV === 'production' ? "/api/" : "http://localhost:5000/api/";

/**
 * Make GET request and dipatch the image data to be shown via redux  
 * @param e
 * @param type
 * @param ageGroup
 */
export function UpdateAuth() {
    
    return ((dispatch: Dispatch<JwtAuthActionTypes>) => {

        return Promise.resolve({
            cookie: "test cookie",
            loggedIn: false,
            userName: "guest"
        });
    });
};

