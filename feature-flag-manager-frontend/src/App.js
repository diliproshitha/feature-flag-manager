import { Routes, Route } from 'react-router-dom';
import './App.css';
import FeatureSummary from './component/featureSummary/FeatureSummary';
import FeatureDetail from './component/featureDetail/FeatureDetail';
import { ChakraProvider, Heading, theme } from '@chakra-ui/react'

const App = () => {
  return (
    <ChakraProvider theme={theme}>
      <div className="App">
      <header className="App-header">
        <div><Heading>Feature Toggle Manager</Heading></div>
        <hr />
      </header>
      <Routes>
          <Route path="/" element={<FeatureSummary />} />
          <Route path="/create" element={<FeatureDetail />} />
          <Route path="/update" element={<FeatureDetail />} />
       </Routes>
    </div>
    </ChakraProvider>
  );
}

export default App;
