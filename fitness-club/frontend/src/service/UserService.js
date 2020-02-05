import axios from 'axios';

const USERS_URL = '/users';

export const userService = {
    getAll,
    deleteById
};

function getAll(){
    return axios.get(USERS_URL);
}

function deleteById(id){
    return axios.delete(USERS_URL + '/' + id);
}