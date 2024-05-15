import React, { useState } from 'react';
import './CounterSelector.css';

function CounterSelector({ onSelectCounter }) {
    const [selectedCounter, setSelectedCounter] = useState('01');
    const counters = ['01', '02', '03', '04', '05'];

    const handleCounterSelect = (counter) => {
        setSelectedCounter(counter);
        onSelectCounter(counter);
    };

    return (
        <div>
            {counters.map(counter => (
                <button
                    key={counter}
                    onClick={() => handleCounterSelect(counter)}
                    className={`button-counter ${selectedCounter === counter? 'selected' : ''}`}
                >
                    Counter <b>{counter}</b>
                </button>
            ))}
        </div>
    );
}

export default CounterSelector;

