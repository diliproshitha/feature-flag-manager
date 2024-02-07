import { useEffect, useState } from "react";
import { FeatureToggle, FeatureToggleStatus } from "../../model/featureToggle";
import React from "react";
import { Button, Container, Heading, IconButton, useToast } from "@chakra-ui/react";
import { ArrowBackIcon } from "@chakra-ui/icons";
import "./FeatureDetail.css";
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';
import { Customer } from "../../model/customer";
import { customerService } from "../../service/customer.service";
import FeatureDetails from "../featureDetailForm/FeatureDetailForm";
import { toInstant } from "../../util/momentUtil";
import { featureToggleService } from "../../service/featureToggle.service";

const FeatureDetail = () => {

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const location = useLocation();
    const toast = useToast()
    const [feature, setFeature] = useState<FeatureToggle>({inverted: false, technicalName: '', status: FeatureToggleStatus.ACTIVE});
    const [customers, setCustomers] = useState<Customer[]>([]);
    const [expiryDate, setExpiryDate] = useState<string>('');
    const [isUpdateScreen, setIsUpdateScreen] = useState<boolean>(false);

    useEffect(() => {
        customerService.byPage(0, 10).then((response) => {
            setCustomers(response.content);
        });
    }, []);

    useEffect(() => {
        if (feature.expiresOn) {
            setExpiryDate(feature.expiresOn);
        }
    }, [feature]);

    useEffect(() => {
        const featureId = searchParams.get('id');
        if (featureId && location.pathname === '/update') {
            setIsUpdateScreen(true);
            featureToggleService.byId(featureId).then((response) => {
                setFeature(response);
            });
        }
    }, [location, searchParams]);

    const setTechnicalNameCallback = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFeature({...feature, technicalName: event.target.value});
    }

    const setDisplayNameCallback = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFeature({...feature, displayName: event.target.value});
    }

    const setExpiresOnCallback = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFeature({...feature, expiresOn: toInstant(event.target.value)});
    }

    const setDescriptionCallback = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        setFeature({...feature, description: event.target.value});
    }

    const setCustomerIdsCallback = (options: any) => {
        setFeature({...feature, customerIds: options.map((option: any) => option.value)});
    }

    const setInvertedCallback = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFeature({...feature, inverted: event.target.checked});
    }

    const handleArchive = () => {
        featureToggleService.archive(feature.id || '')
        .then(() => {
            showToast("Feature Archived", "Feature has been archived successfully", "success");
            navigate("/");
        })
        .catch((error) => {
            const errorMessage = error?.response?.data?.message || "An unknown error occurred while archiving the feature";
            showToast("Feature Archive Failed", errorMessage, "error");
        });
    }

    const showToast = (title: string, description: string, status: "success" | "error") => {
        toast({
            title: title,
            description: description,
            status: status,
            duration: 5000,
            isClosable: true,
        });
    }

    const handleUpdate = () => {
        featureToggleService.update({...feature, expiresOn: toInstant(expiryDate)})
        .then(() => {
            showToast("Feature Updated", "Feature has been updated successfully", "success");
            navigate("/");
        })
        .catch((error) => {
            const errorMessage = error?.response?.data?.message || "An unknown error occurred while creating the feature";
            showToast("Feature Creation Failed", errorMessage, "error");
        });
    }

    const handleSave = () => {
        featureToggleService.save(feature)
        .then(() => {
            showToast("Feature Created", "Feature has been created successfully", "success");
            navigate("/");
        })
        .catch((error) => {
            const errorMessage = error?.response?.data?.message || "An unknown error occurred while creating the feature";
            showToast("Feature Creation Failed", errorMessage, "error");
        });
    }

    const showCreateBtn = () => {
        return !isUpdateScreen;
    }

    const showUpdateBtn = () => {
        return isUpdateScreen && feature.status === FeatureToggleStatus.ACTIVE;
    }

    const showDisabledArchivedBtn = () => {
        return feature.status === FeatureToggleStatus.ARCHIVED;
    }

    return (
        <div className="CreateFeatureWrapper">
            <Heading margin={'0 0 20px 0'} as="h2" size='lg'>{isUpdateScreen ? 'Update Feature' : 'Create Feature'}</Heading>
            <FeatureDetails 
                feature={feature}
                setTechnicalNameCallback={setTechnicalNameCallback} 
                setDisplayNameCallback={setDisplayNameCallback} 
                setExpiresOnCallback={setExpiresOnCallback} 
                setDescriptionCallback={setDescriptionCallback} 
                setCustomerIdsCallback={setCustomerIdsCallback}
                setInvertedCallback={setInvertedCallback} 
                customers={customers} />
            <Container>
                <div className="ButtonSection">
                    <IconButton
                        isRound={true}
                        variant='solid'
                        colorScheme='teal'
                        aria-label='Done'
                        fontSize='20px'
                        onClick={() => navigate("/")}
                        icon={<ArrowBackIcon />}
                    />
                    <div>
                        {showCreateBtn() && <Button colorScheme='teal' variant='solid' onClick={handleSave}>Create</Button>}
                        {showUpdateBtn() && <Button borderColor='green.500' border={'2px'} margin={'0 10px 0 0'} onClick={handleArchive}>Archive</Button>}
                        {showUpdateBtn() && <Button colorScheme='orange' variant='solid' onClick={handleUpdate}>Update</Button>}
                        {showDisabledArchivedBtn() && <Button colorScheme='gray' variant='solid' pointerEvents={'none'} opacity={'70%'} disabled={true}>Archived</Button>}
                    </div>
                    
                </div>
            </Container>
        </div>
    );
};

export default FeatureDetail;