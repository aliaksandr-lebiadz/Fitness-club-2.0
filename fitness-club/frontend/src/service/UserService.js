import axios from 'axios';

const GET_USERS_URL = '/users';

export const userService = {
    getAll
};

function getAll(){
    return axios.get(GET_USERS_URL);
}