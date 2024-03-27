import React, { useState, useEffect } from "react";
import List from "../components/List";
import { useLocation } from "react-router-dom";
import { useNavigate} from "react-router-dom";

import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getSortedRowModel,
  getPaginationRowModel,
} from "@tanstack/react-table";
import FilterBarDoctorList from "../components/FilterBarDoctorList";

import StarsRenderer from "../components/StarsRenderer";

const columnHelper = createColumnHelper();

export default function DoctorList() {
  const location = useLocation();
  const navigate = useNavigate();
  const [selectedLocation, setSelectedLocation] = useState();
  const [selectedRow, setSelectedRow] = useState();
  const [data, setData] = useState(
    JSON.parse(localStorage.getItem("doctorListData"))
  );

  useEffect(() => {
    if (location.state) {
      setData(location.state);
      localStorage.setItem("doctorListData", JSON.stringify(location.state));

    }
  }, []);

  const handleSelectedLocation = (index, location) => {
    setSelectedLocation({location:location, index:index})
  }

  const handleSelectedVisit = (doctors, visit) => {
    const doctor = doctors[selectedRow]
    const data = { visitType: visit.type,
                   visitPrice: visit.price,
                   visitId: visit.id,
                   doctorName: doctor.name+" "+doctor.surname, 
                   doctorEmail: doctor.email,
                   doctorPhoneNumber: doctor.attributes.phoneNumber[0],
                   locationAddress: selectedLocation.location.address,
                   locationName: selectedLocation.location.name,
                   contactInfo: selectedLocation.location.contactInfo[0]
                   };
    
    navigate("/appointments/reservation", { state: data });
    console.log(data)
  }

  const handleRowClick = (rowIndex) => {
    setSelectedRow(rowIndex);
  };

  const columns = [
    columnHelper.accessor("Doctor Profile", {
      cell: (info) => (
        <div className="flex items-center">
          <div className="flex-shrink-0 h-16 w-16">
            <img
              className="h-full w-full rounded-full"
              src={info.row.original.attributes.imageLink[0]}
              alt="Doctor Image here"
            />
          </div>
          <div className="ml-4">
            <div className="text-lg font-semibold text-gray-900 mb-2">
              {info.row.original.name} {info.row.original.surname}
            </div>
            <div className="text-sm text-gray-700 mb-2">
              {info.row.original.specializations[0]}
            </div>
            <div className="flex mt-1"><StarsRenderer reviews={info.row.original.reviews}></StarsRenderer></div>
            <div className="text-sm text-gray-400 mb-2">
              {info.row.original.reviewsNumber} reviews
            </div>
          </div>
        </div>
      ),
    }),
    columnHelper.accessor("Location", {
      cell: (info) => (
        <div>
          <List
            items={info.row.original.locations.map((loc) => loc.address)}
            label={"Select location"}
            onItemClick={(index) => handleSelectedLocation(index, info.row.original.locations[index])}
          />
        </div>
      ),
    }),
    columnHelper.accessor("Visit Types", {
      cell: (info) => (
        <div>
          <List
            items={
              selectedLocation
                ? selectedLocation.location.visits.map((visit) => visit.type)
                : []
            }
            label={"Select visit"}
            onItemClick={ (index) => handleSelectedVisit(data.doctors, info.row.original.locations[selectedLocation.index].visits[index])}
          />
        </div>
      ),
    }),
  ];

  const table = useReactTable({
    data: data.doctors,
    columns,
    initialState: {
      pagination: {
        pageSize: 4,
      },
    },
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <div className="container mx-auto flex h-max py-24 gap-24">
      <FilterBarDoctorList filters={data.filters} />
      <div className="flex-row items-center overflow-x-auto w-full max-w-screen-xl">
        <table className="border w-full">
          <thead>
            {table.getHeaderGroups().map((headerGroup) => (
              <tr
                key={headerGroup.id}
                className="border-b text-white uppercase bg-blue-500"
              >
                {headerGroup.headers.map((header) => (
                  <th key={header.id} className="px-4 pr-2 py-4  text-left">
                    {header.isPlaceholder ? null : (
                      <div
                        {...{
                          className: header.column.getCanSort()
                            ? "cursor-pointer select-none flex min-w-[36px]"
                            : "",
                          onClick: header.column.getToggleSortingHandler(),
                        }}
                      >
                        {flexRender(
                          header.column.columnDef.header,
                          header.getContext()
                        )}
                        {{
                          asc: <span className="pl-2">↑</span>,
                          desc: <span className="pl-2">↓</span>,
                        }[header.column.getIsSorted()] ?? null}
                      </div>
                    )}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody>
            {table.getRowModel().rows.map((row) => (
              <tr key={row.id} className="bg-white" onClick={() => handleRowClick(row.id)}>
                {row.getVisibleCells().map((cell) => (
                  <td key={cell.id} className="px-4 pt-[14px] pb-[18px]">
                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
        <div className="flex justify-center w-full mt-8">
          <div className="flex gap-2">
            <button
              className="hover:bg-blue-500 hover:cursor-pointer rounded-lg bg-blue-700 p-1 h-8"
              onClick={() => table.setPageIndex(0)}
              disabled={!table.getCanPreviousPage()}
            >
              <span className="w-1 h-1 text-white font-bold">{"<<"}</span>
            </button>
            <button
              className="hover:bg-blue-500 hover:cursor-pointer rounded-lg bg-blue-700 p-1 h-8"
              onClick={() => table.previousPage()}
              disabled={!table.getCanPreviousPage()}
            >
              <span className="w-5 h-5 text-white font-bold">{"<"}</span>
            </button>
            <span
              className="text-white font-bold"
              style={{ whiteSpace: "nowrap" }}
            >
              {table.getState().pagination.pageIndex + 1} of{" "}
              {table.getPageCount()}
            </span>
            <button
              className="hover:bg-blue-500 hover:cursor-pointer rounded-lg bg-blue-700 p-1 h-8"
              onClick={() => table.nextPage()}
              disabled={!table.getCanNextPage()}
            >
              <span className="w-5 h-5 text-white font-bold">{">"}</span>
            </button>
            <button
              className="hover:bg-blue-500 hover:cursor-pointer rounded-lg bg-blue-700 p-1 h-8"
              onClick={() => table.setPageIndex(table.getPageCount() - 1)}
              disabled={!table.getCanNextPage()}
            >
              <span className="w-5 h-5 text-white font-bold">{">>"}</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
