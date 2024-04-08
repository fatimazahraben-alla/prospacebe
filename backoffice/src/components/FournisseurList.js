import React, { useEffect, useState } from 'react';
import axios from 'axios';

const FournisseursList = () => {
    const [fournisseurs, setFournisseurs] = useState([]);

    useEffect(() => {
        axios.get('http://your-api-url/api/fournisseurs')
             .then((response) => setFournisseurs(response.data))
             .catch((error) => console.error(error));
    }, []);

    return (
        <div>
            <h2>Liste des Fournisseurs</h2>
            <ul>
                {fournisseurs.map(fournisseur => (
                    <li key={fournisseur.id}>{fournisseur.nom}</li>
                ))}
            </ul>
        </div>
    );
};

export default FournisseursList;
