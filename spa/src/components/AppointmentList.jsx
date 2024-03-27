import * as React from "react";
import "../Output.css";
import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getSortedRowModel,
  getPaginationRowModel,
} from "@tanstack/react-table";
import FilterBarDoctorAppointmentList from "./FilterBarDoctorAppointmentList";

const columnHelper = createColumnHelper();

const columns = [
  columnHelper.accessor("visit type", {
    cell: (info) => info.getValue(),
  }),
  columnHelper.accessor("location", {
    cell: (info) => info.getValue(),
  }),
  columnHelper.accessor("price", {
    cell: (info) => info.getValue(),
  }),
  columnHelper.accessor("patient info", {
    cell: (info) => info.renderValue(),
  }),
  columnHelper.accessor("status", {
    // Add status column
    cell: (info) => info.getValue(),
  }),
  columnHelper.accessor("timestamp+timeslot", {
    // Add timestamp+timeslot column
    cell: (info) => info.getValue(),
  }),
];

const data = [
  {
    "visit type": "Type A",
    location: "Location A",
    price: "$100",
    "patient info": "John Doe, johndoe@example.com, 123-456-7890",
    status: "PENDING ⏳",
    "timestamp+timeslot": "30 min",
  },
  {
    "visit type": "Type A",
    location: "Location A",
    price: "$100",
    "patient info": "John Doe, johndoe@example.com, 123-456-7890",
    status: "CONFIRMED ✔️",
    "timestamp+timeslot": "1h",
  },
  {
    "visit type": "Type A",
    location: "Location A",
    price: "$100",
    "patient info": "John Doe, johndoe@example.com, 123-456-7890",
    status: "REJECTED ❌",
    "timestamp+timeslot": "1h30",
  },
  // Add more data as needed
];

export default function App() {
  const [sorting, setSorting] = React.useState([]);
  const [selectedRow, setSelectedRow] = React.useState(null);
  const [showPopup, setShowPopup] = React.useState(false);
  const handleFilterChange = (filter) => {
    // Implement filtering logic here
    console.log("Filter:", filter);
  };

  const table = useReactTable({
    data,
    columns,
    state: {
      sorting,
    },
    initialState: {
      pagination: {
        pageSize: 4,
      },
    },
    getCoreRowModel: getCoreRowModel(),
    onSortingChange: setSorting,
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  const handleRowClick = (row) => {
    setSelectedRow(row);
  };

  const handleConfirmClick = () => {
    setShowPopup(true);
  };

  const handleRejectClick = () => {
    setShowPopup(true);
  };

  const handlePopupClose = () => {
    setShowPopup(false);
    setSelectedRow(null); 
  };

  return (
    <div className="container mx-auto flex h-screen py-24 gap-24">
      {/* Filter bar */}
      <FilterBarDoctorAppointmentList handleFilterChange={handleFilterChange} />
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
              <tr
                key={row.id}
                className={selectedRow === row ? "bg-blue-300" : "bg-white"}
                onClick={() => handleRowClick(row)}
              >
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
              className="text-black font-bold"
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
          <div className="flex justify-center w-full mt-8">
            <div className="flex justify-center mt-4">
              <button
                className="bg-green-500 text-white font-semibold px-6 py-3 rounded-md mr-4 hover:bg-green-300"
                onClick={handleConfirmClick}
              >
                Confirm
              </button>
              <button
                className="bg-red-500 text-white font-semibold px-6 py-3 rounded-md hover:bg-red-300"
                onClick={handleRejectClick}
              >
                Reject
              </button>
            </div>
          </div>
        </div>
      </div>
      {showPopup && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            {/* Popup content */}
            <h2 className="text-xl font-semibold mb-4">Insert Notes</h2>
            <textarea
              className="w-full h-32 border rounded-md p-2 mb-4"
              placeholder="Insert notes here..."
            ></textarea>
            <div className="flex justify-end">
              <button
                className="bg-blue-500 text-white font-semibold px-6 py-3 rounded-md mr-4"
                onClick={() => handlePopupClose()} // Close popup and reset selected row
              >
                Cancel
              </button>
              <button
                className="bg-green-500 text-white font-semibold px-6 py-3 rounded-md"
                onClick={() => handlePopupClose()} // Close popup and reset selected row
              >
                Submit
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
