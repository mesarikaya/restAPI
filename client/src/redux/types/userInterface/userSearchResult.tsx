export interface UserSearchResult {
    firstName: string,
    middleName: string,
    email: string,
    oauthId: string,
    address: string,
    members: {userNames: string[]}
}