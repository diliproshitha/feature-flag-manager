import axios from 'axios';

export const featureAxios = axios.create({
    baseURL: process.env.REACT_APP_FEATURE_FLAG_MANAGER_SERVICE_URL + '/api/v1/feature',
});

export const customerAxios = axios.create({
    baseURL: process.env.REACT_APP_FEATURE_FLAG_MANAGER_SERVICE_URL + '/api/v1/customer',
});
