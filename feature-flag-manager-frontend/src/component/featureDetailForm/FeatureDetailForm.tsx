import { FC, useEffect, useState } from "react";
import { FeatureToggleStatus, FeatureToggle } from "../../model/featureToggle";
import { Container, Text, Input, Textarea, Switch } from "@chakra-ui/react";
import React from "react";
import { Select } from "chakra-react-select";
import { Customer } from "../../model/customer";
import { toLocateDate } from "../../util/momentUtil";
import './FeatureDetailForm.css';

type FeatureDetailFormProps = {
    feature: FeatureToggle,
    setTechnicalNameCallback: any,
    setDisplayNameCallback: any,
    setExpiresOnCallback: any,
    setDescriptionCallback: any,
    setCustomerIdsCallback: any
    setInvertedCallback: any,
    customers: Customer[]
}

const FeatureDetailForm: FC<FeatureDetailFormProps> = ({
    feature,
    setTechnicalNameCallback,
    setDisplayNameCallback,
    setExpiresOnCallback,
    setDescriptionCallback,
    setCustomerIdsCallback,
    setInvertedCallback,
    customers
}) => {

    const [customerSelectOptions, setCustomerSelectOptions] = useState<{label: string, value: string}[]>([]);

    useEffect(() => {
        customers && setCustomerSelectOptions(getCustomerOptions(customers));
    }, [customers, feature]);

    const getCustomerOptions = (customers: Customer[]) => {
        return customers.map((customer) => {
            return {label: customer.firstName + " " + customer.lastName, value: customer.id};
        });
    }

    const getCustomerSelectDefaultValues = () => {
        const options = customerSelectOptions.filter((option) => {
            return feature.customerIds?.includes(option.value);
        });
        return options;
    }

    return (
        <Container className={feature.status === FeatureToggleStatus.ARCHIVED ? 'Disabled' : ''}>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Technical Name:</Text></div>
                <div className="FeatureAttributeWrapper"><Input value={feature.technicalName} onChange={setTechnicalNameCallback} size='md' /></div>
            </div>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Display Name:</Text></div>
                <div className="FeatureAttributeWrapper"><Input value={feature.displayName} onChange={setDisplayNameCallback} size='md' /></div>
            </div>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Expires on:</Text></div>
                <div className="FeatureAttributeWrapper"><Input value={feature.expiresOn ? toLocateDate(feature.expiresOn) : ''} onChange={setExpiresOnCallback} size='md' type="datetime-local" /></div>
            </div>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Description:</Text></div>
                <div className="FeatureAttributeWrapper"><Textarea value={feature.description} onChange={setDescriptionCallback} fontSize='lg'></Textarea></div>
            </div>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Customers:</Text></div>
                <div className="FeatureAttributeWrapper">
                    <Select
                        placeholder=""
                        isMulti
                        colorScheme="purple"
                        className="MultiSelect"
                        options={customerSelectOptions}
                        value={getCustomerSelectDefaultValues()}
                        onChange={setCustomerIdsCallback}
                    />
                </div>
            </div>
            <div className="FeatureDescWrapper">
                <div className="FeatureAttributeWrapper"><Text fontSize='lg'>Inverted:</Text></div>
                <div className="FeatureAttributeWrapper"><Switch onChange={setInvertedCallback} size='lg' isChecked={feature.inverted} /></div>
            </div>
        </Container>
    );
}

export default FeatureDetailForm;