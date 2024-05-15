import React, { useState } from 'react';
import './CategoryDisplay.css';

function CategoryDisplay() {
    const [numbers, setNumbers] = useState({ A: 1, B: 1, C: 1, D: 1});

    const handleIncrement = (category) => {
        setNumbers(prev => ({...prev, [category]: prev[category] + 1 }));
    };    

    return (
        <div>
            <div className="card-category-container">
                {Object.entries(numbers).map(([category, number], index) => (
                    <div key={category} className={`card-category`}>
                        <span>{category}{number}</span>
                        <button onClick={() => handleIncrement(category)} className="call-next-button">Call Next</button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default CategoryDisplay;
