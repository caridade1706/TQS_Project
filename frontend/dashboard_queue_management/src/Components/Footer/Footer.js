import React from 'react';
import './Footer.css';  // Certifique-se de criar este arquivo CSS no mesmo diretório

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <p>© 2024 CliniConnect. All rights reserved.</p>
        <div className="footer-links">
          <a href="#about">About Us</a>
          <a href="#services">Services</a>
          <a href="#contact">Contact</a>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
