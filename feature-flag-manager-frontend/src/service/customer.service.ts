import { customerAxios } from "../config/axios.config";
import { Customer } from "../model/customer";
import { Page } from "../model/page";

export const customerService = {
    byPage: async (page: number, size: number): Promise<Page<Customer>> => {
        const response = await customerAxios.get(`/byPage?page=${page}&size=${size}`);
        return response.data;
    }
}