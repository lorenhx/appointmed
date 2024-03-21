import * as React from "react";
import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getSortedRowModel,
  getPaginationRowModel,
} from "@tanstack/react-table";
import FilterBar from "./FilterBar";
import List from "./List";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

const renderStars = (rating) => {
  const stars = [];
  for (let i = 0; i < 5; i++) {
    stars.push(
      <span key={i}>
        <FontAwesomeIcon
          icon={faStar}
          className={i < rating ? "text-yellow-400" : "text-gray-400"}
        />
      </span>
    );
  }
  return stars;
};

const handleFilterChange = () => {};

const columnHelper = createColumnHelper();

const columns = [
  columnHelper.accessor("Doctor Profile", {
    cell: (info) => (
      <div className="flex items-center">
        <div className="flex-shrink-0 h-16 w-16">
          <img
            className="h-full w-full rounded-full"
            src={info.row.original.reviewData.profilePhoto}
            alt="Doctor"
          />
        </div>
        <div className="ml-4">
          <div className="text-lg font-semibold text-gray-900 mb-2">
            {info.row.original.reviewData.name}
          </div>
          <div className="text-sm text-gray-500 mb-2">
            {info.row.original.reviewData.mainSpecialization}
          </div>
          <div className="flex mt-1">
            {renderStars(info.row.original.reviewData.rating)}
          </div>
        </div>
      </div>
    ),
  }),
  columnHelper.accessor("Location", {
    cell: (info) => (
      <List
        items={info.row.original.locations.map((loc) => loc.location)}
        label={info.row.original.locationsLabel}
        onItemClick={info.row.original.onItemClick}
      />
    ),
  }),
  columnHelper.accessor("Visit Types", {
    cell: (info) => (
      <List
        items={info.row.original.strings}
        label={info.row.original.visitsLabel}
        onItemClick={info.row.original.onItemClick}
      />
    ),
  }),
];

export default function AppointmentTable({ doctorData }) {
  const [sorting, setSorting] = React.useState([]);
  const [showPopup, setShowPopup] = React.useState(false);

  const table = useReactTable({
    data: doctorData,
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

  const handleReserveClick = () => {
    setShowPopup(true);
  };

  const handlePopupClose = () => {
    setShowPopup(false);
  };

  return (
    <div className="container mx-auto flex h-screen py-24 gap-24">
      <FilterBar handleFilterChange={handleFilterChange} />
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
              <tr key={row.id} className="bg-white">
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
