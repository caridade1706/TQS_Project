import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './CategoryDisplay.css';

function CategoryDisplay({ selectedCounter }) {
    const [numbers, setNumbers] = useState({ A: 0, B: 0, C: 0 });

    useEffect(() => {
        try {
            const savedNumbers = JSON.parse(localStorage.getItem('numbers'));
            if (savedNumbers) {
                setNumbers(savedNumbers);
            }
        } catch (error) {
            console.error("Error loading numbers from localStorage:", error);
        }
    }, []);


    const handleIncrement = (category) => {
        const counter = selectedCounter;
        const url = process.env.REACT_APP_API_URL + `queue-management/call-next?priorityStatus=${category}&counter=${counter}`;
        console.log("Calling next ticket for category:", category);
        console.log("Counter:", counter);
        axios
            .get(url)
            .then((response) => {
                if (response.status === 204) {
                    alert("No content available for the next ticket.");
                } else {
                    setNumbers(prev => {
                        const newNumbers = { ...prev, [response.data.priorityStatus]: response.data.queueNumber.slice(-1) };
                        localStorage.setItem('numbers', JSON.stringify(newNumbers));
                        return newNumbers;
                    });
                    console.log(response.data);
                }
            })
            .catch((error) => {
                console.error("Error calling next ticket:", error);
                alert("Failed to call next ticket");
            });
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