import { featureAxios } from "../config/axios.config";
import { FeatureToggle } from "../model/featureToggle";
import { Page } from "../model/page";

export const featureToggleService = {
    byPage: async (page: number, size: number): Promise<Page<FeatureToggle>> => {
        const response = await featureAxios.get(`/byPage?page=${page}&size=${size}&sort=createdAt`);
        return response.data;
    },
    save: async (featureToggle: FeatureToggle): Promise<FeatureToggle> => {
        const response = await featureAxios.post("", featureToggle);
        return response.data;
    },
    update: async (featureToggle: FeatureToggle): Promise<FeatureToggle> => {
        const response = await featureAxios.put("", featureToggle);
        return response.data;
    },
    byId: async (id: string): Promise<FeatureToggle> => {
        const response = await featureAxios.get(`/byId/${id}`);
        return response.data;
    },
    archive: async (id: string): Promise<FeatureToggle> => {
        const response = await featureAxios.patch(`/${id}/archive`);
        return response.data;
    }
}