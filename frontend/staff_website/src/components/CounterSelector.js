import React, { useState } from 'react';

function CounterSelector({ onSelectCounter }) {
    const [selectedCounter, setSelectedCounter] = useState('01');
    const counters = ['01', '02', '03', '04', '05'];

    const handleCounterSelect = (counter) => {
        setSelectedCounter(counter);
        onSelectCounter(counter);
    };

    return (
        <div>
            <h3>Select Counter:</h3>
            {counters.map(counter => (
                <button key={counter} onClick={() => handleCounterSelect(counter)} style={{ margin: '5px', padding: '10px' }}>
                    Counter {counter}
                </button>
            ))}
        </div>
    );
}

export default CounterSelector;
