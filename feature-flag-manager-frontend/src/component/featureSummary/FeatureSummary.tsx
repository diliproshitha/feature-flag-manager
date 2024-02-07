import { useEffect, useState } from "react";
import { featureToggleService } from "../../service/featureToggle.service";
import { FeatureToggle } from "../../model/featureToggle";
import React from "react";
import {Table, Thead, Tbody, Tr, Th, Td, TableContainer, Button, IconButton } from '@chakra-ui/react'
import "./FeatureSummary.css";
import { ArrowForwardIcon } from "@chakra-ui/icons";
import { useNavigate } from 'react-router-dom';

const FeatureSummary = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [features, setFetures] = useState<FeatureToggle[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoading(true);
        featureToggleService.byPage(0, 10).then((response) => {
            setFetures(response.content);
            setIsLoading(false);
        })
    }, []);

    const isExpired = (expiresOn: string) => {
        const expirationDate = new Date(expiresOn);
        const currentDate = new Date();
        return currentDate > expirationDate;
    }

    const handleNavigateToUpdate = (id: string) => {
        navigate(`update?id=${id}`);
    }

    return (
        <div className="SummaryWrapper">
            <div className="CreateFeatureBtn">
                <Button colorScheme='teal' variant='solid' onClick={() => navigate('create')}>Create Feature</Button></div>
                <TableContainer>
                    <Table variant='simple'>
                        <Thead>
                            <Tr>
                                <Th>Name</Th>
                                <Th>Inverted</Th>
                                <Th>Status</Th>
                                <Th>Expired</Th>
                                <Th></Th>
                            </Tr>
                        </Thead>
                        <Tbody>
                            {
                                features.map((row, index) => (
                                    <Tr key={index}>
                                        <Td>{row.displayName || row.technicalName}</Td>
                                        <Td>{row.inverted ? 'Yes' : 'No'}</Td>
                                        <Td>{row.status}</Td>
                                        <Td>{row.expiresOn && isExpired(row.expiresOn) ? 'Yes' : 'No'}</Td>
                                        <Td>
                                        <IconButton
                                            onClick={() => handleNavigateToUpdate(row.id || '')}
                                            isRound={true}
                                            variant='solid'
                                            colorScheme='teal'
                                            aria-label='Done'
                                            fontSize='20px'
                                            icon={<ArrowForwardIcon />}
                                        />
                                        </Td>
                                    </Tr>
                                ))
                            }
                        </Tbody>
                    </Table>
                </TableContainer>
        </div>
    );
};

export default FeatureSummary;