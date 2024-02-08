import { useEffect, useState } from "react";
import { featureToggleService } from "../../service/featureToggle.service";
import { FeatureToggle } from "../../model/featureToggle";
import React from "react";
import {Table, Thead, Tbody, Tr, Th, Td, TableContainer, Button, IconButton } from '@chakra-ui/react'
import "./FeatureSummary.css";
import { ArrowForwardIcon } from "@chakra-ui/icons";
import { useNavigate } from 'react-router-dom';
import { Pagination, PaginationProps } from 'antd';

const FeatureSummary = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [features, setFetures] = useState<FeatureToggle[]>([]);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);
    const [totalFeatures, setTotalFeatures] = useState(0); // TODO: use this to set the total in the pagination component
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoading(true);
        featureToggleService.byPage(page - 1, pageSize).then((response) => {
            setFetures(response.content);
            setTotalFeatures(response.totalElements);
            setIsLoading(false);
        })
    }, [page, pageSize]);

    const isExpired = (expiresOn: string) => {
        const expirationDate = new Date(expiresOn);
        const currentDate = new Date();
        return currentDate > expirationDate;
    }

    const handleNavigateToUpdate = (id: string) => {
        navigate(`update?id=${id}`);
    }

    const onShowSizeChange: PaginationProps['onShowSizeChange'] = (current, pageSize) => {
        setPageSize(pageSize);
      };

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
                            <Tr>
                                <Pagination 
                                    showQuickJumper 
                                    showSizeChanger 
                                    pageSize={pageSize}
                                    onShowSizeChange={onShowSizeChange}
                                    defaultCurrent={page} 
                                    pageSizeOptions={['5', '10', '20', '50']}
                                    total={totalFeatures}
                                    onChange={setPage} 
                                />
                            </Tr>
                        </Tbody>
                    </Table>
                </TableContainer>
        </div>
    );
};

export default FeatureSummary;