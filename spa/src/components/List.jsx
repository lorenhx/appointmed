import React from "react";

const List = ({ items, onItemClick, label }) => {
  return (
    <div className="overflow-y-auto max-h-36 shadow-md bg-white p-4 ">    
  
      <h3 className="text-lg font-semibold mb-2">{label}</h3>
      <ul>
        {items.map((item, index) => (
          <li
            key={index}
            className="text-black mb-2 cursor-pointer transition-colors duration-300 hover:bg-blue-500"
            onClick={() => onItemClick(index)}
          >
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default List;
