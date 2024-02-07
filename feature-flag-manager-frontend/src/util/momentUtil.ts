import moment from "moment";

export const toInstant = (dateStr: string): string => {
    const targetFormat = "YYYY-MM-DDTHH:mm:ss.SSSSSSZ";
    const parsedDate = moment(dateStr);
    const adjustedDate = parsedDate.utcOffset(moment().utcOffset());
    return adjustedDate.format(targetFormat);
}

export const toLocateDate = (dateStr: string): string => {
    const targetFormat = "YYYY-MM-DDTHH:mm";
    const parsedDate = moment(dateStr);
    return parsedDate.format(targetFormat);
}