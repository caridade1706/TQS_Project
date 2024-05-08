import React, { useState } from 'react';

function CategoryDisplay() {
    const [numbers, setNumbers] = useState({ A: 1, B: 1, C: 1, D: 1 });

    const handleIncrement = (category) => {
        setNumbers(prev => ({ ...prev, [category]: prev[category] + 1 }));
    };

    return (
        <div>
            <h3>Categories:</h3>
            {Object.entries(numbers).map(([category, number]) => (
                <div key={category}>
                    <span>{category}: {number} </span>
                    <button onClick={() => handleIncrement(category)}>Call Next</button>
                </div>
            ))}
        </div>
    );
}

export default CategoryDisplay;
