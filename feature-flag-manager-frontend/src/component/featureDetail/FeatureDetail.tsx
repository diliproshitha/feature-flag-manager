import { useEffect, useRef, useState } from "react";
import { FeatureToggle, FeatureToggleStatus } from "../../model/featureToggle";
import React from "react";
import { AlertDialog, AlertDialogBody, AlertDialogCloseButton, AlertDialogContent, AlertDialogFooter, AlertDialogHeader, AlertDialogOverlay, Button, Container, Heading, IconButton, useDisclosure, useToast } from "@chakra-ui/react";
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
    const toast = useToast();
    const [feature, setFeature] = useState<FeatureToggle>(
        {inverted: false, 
            technicalName: '', 
            status: FeatureToggleStatus.ACTIVE,
            customerIds: []
        }
    );
    const [customers, setCustomers] = useState<Customer[]>([]);
    const [isUpdateScreen, setIsUpdateScreen] = useState<boolean>(false);
    const { isOpen, onOpen, onClose } = useDisclosure()
  const cancelRef = useRef(null)

    useEffect(() => {
        customerService.byPage(0, 10).then((response) => {
            setCustomers(response.content);
        });
    }, []);

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

    const validateFeature = (feature: FeatureToggle): boolean => {
        const isTechnicalNameValid = validateTechnicalName(feature);
        const isExpiryDateValid = validateExpiresOn(feature);
        return isTechnicalNameValid && isExpiryDateValid;
    }
    
    const validateTechnicalName = (feature: FeatureToggle): boolean => {
        let isValid = true;
        if (!feature.technicalName || feature.technicalName.length === 0) {
            isValid = false;
            showToast('Technical Name is required', 'Please enter a technical name', 'error');
        }
        return isValid;
    }
    
    const validateExpiresOn = (feature: FeatureToggle): boolean => {
        let isValid = true;
        if (!feature.expiresOn || feature.expiresOn.length === 0) {
            isValid = false;
            showToast('Expiry Date is required', 'Please enter a expiry date', 'error');
        }
        return isValid;
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
        if (!validateFeature(feature)) {
            return;
        }
        featureToggleService.update(feature)
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
        if (!validateFeature(feature)) {
            return;
        }
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

    const handleGoBack = () => {
        if (isFormDirty()) {
            showGoBackWarning();
        } else {
            navigateToHome();
        }
    }

    const navigateToHome = () => {
        navigate("/");
    }

    const showGoBackWarning = () => {
        onOpen();
    }

    const isFormDirty = () => {
        return !isUpdateScreen
        && (feature.technicalName 
        || feature.displayName 
        || feature.expiresOn 
        || feature.description 
        ||  feature.customerIds && feature.customerIds.length > 0 
        || feature.inverted);
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
                        onClick={handleGoBack}
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
            <AlertDialog
                motionPreset='slideInBottom'
                leastDestructiveRef={cancelRef}
                onClose={onClose}
                isOpen={isOpen}
                isCentered
            >
                <AlertDialogOverlay />

                <AlertDialogContent>
                <AlertDialogHeader>Discard Changes?</AlertDialogHeader>
                <AlertDialogCloseButton />
                <AlertDialogBody>
                    Feature has not been saved yet. Are you sure you want to discard all of your changes?
                </AlertDialogBody>
                <AlertDialogFooter>
                    <Button ref={cancelRef} onClick={onClose}>
                    No
                    </Button>
                    <Button colorScheme='red' ml={3} onClick={navigateToHome}>
                    Yes
                    </Button>
                </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    );
};

export default FeatureDetail;