import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    }
});

// TODO for later, here we`ll be an interceptor here that attaches the JWT token automatically - api.interceptors.request.use(...)

export default api;