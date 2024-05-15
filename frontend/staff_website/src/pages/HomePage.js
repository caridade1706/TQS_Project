import React from 'react';
import CounterSelector from '../components/CounterSelector';
import CategoryDisplay from '../components/CategoryDisplay';
import ConsultationsTable from '../components/ConsultationsTable';

function HomePage() {
    
    

    const handleCounterSelect = (counter) => {
        console.log("Selected counter:", counter);
    };

    return (
        <div>
            <div className="container">
                <CounterSelector onSelectCounter={handleCounterSelect} />
                <CategoryDisplay />
                <ConsultationsTable />
            </div>
        </div>
    );
}

export default HomePage;
