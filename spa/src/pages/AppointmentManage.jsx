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
import FilterBarDoctorAppointmentList from "../components/FilterBarDoctorAppointmentList";
import axios from "axios";
import { useKeycloak } from "@react-keycloak/web";

const columnHelper = createColumnHelper();

const columns = [
  columnHelper.accessor("Visit Type", {
    cell: (info) => info.row.original.visitType,
  }),
  columnHelper.accessor("Issued Timestamp", {
    cell: (info) =>
      new Date(info.row.original.issuedTimestamp).toLocaleString("it-IT", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
      }),
  }),
  columnHelper.accessor("Start Timestamp", {
    cell: (info) =>
      new Date(info.row.original.startTimestamp).toLocaleString("it-IT", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
      }),
  }),
  columnHelper.accessor("Duration", {
    cell: (info) => `${info.row.original.timeSlotMinutes} min`,
  }),
  columnHelper.accessor("Status", {
    cell: (info) => info.row.original.status,
  }),
  columnHelper.accessor("Price", {
    cell: (info) => info.row.original.price,
  }),
  columnHelper.accessor("Address", {
    cell: (info) => info.row.original.address,
  }),
  columnHelper.accessor("Patient Name", {
    cell: (info) =>
      `${info.row.original.patient.name} ${info.row.original.patient.surname}`,
  }),
];

export default function AppointmentManage() {
  // const [sorting, setSorting] = React.useState([]);
  const [selectedRow, setSelectedRow] = React.useState(null);
  const [showPopup, setShowPopup] = React.useState(false);
  const [appointments, setAppointments] = React.useState([]); // State to store fetched appointments
  const [notes, setNotes] = React.useState(""); // State to store notes
  const [status, setStatus] = React.useState(""); // State to store status\
  const [isLoading, setIsLoading] = React.useState(false);
  const { keycloak } = useKeycloak();

  const handleFilterChange = (filter) => {
  };

  React.useEffect(() => {
    axios
      .get(`${import.meta.env.VITE_API_URL}/appointment`, {
        headers: {
          Authorization: `Bearer ${keycloak.token}`, 
        },
      })
      .then((response) => {
        setAppointments(response.data.appointments);
      })
      .catch((error) => {
        alert("Error fetching appointments, try again later.");
      });
  }, []);

  const table = useReactTable({
    data: appointments,
    columns,
    // state: {
    //   sorting,
    // },
    initialState: {
      pagination: {
        pageSize: 5,
      },
    },
    getCoreRowModel: getCoreRowModel(),
    // onSortingChange: setSorting,
    // getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  const handleRowClick = (row) => {
    setSelectedRow(row);
  };

  const handleConfirmClick = () => {
    setStatus("CONFIRMED");
    setShowPopup(true);
  };

  const handleRejectClick = () => {
    setStatus("REJECTED");
    setShowPopup(true); 
  };

  const handlePopupClose = () => {
    setShowPopup(false);
    setSelectedRow(null);
  };

  const handleSubmitClick = () => {
    setIsLoading(true);

    const body = {
      notes: notes,
      status: status,
    };


    axios
      .patch(
        `${import.meta.env.VITE_API_URL}/appointment/${selectedRow.original.id}`,
        body,
        {
          headers: {
            Authorization: `Bearer ${keycloak.token}`, 
          },
        }
      )
      .then((response) => {
        if (status === "CONFIRMED") {
          selectedRow.original.status = "CONFIRMED";
          alert(`Appointment successfully confirmed`);
        } else {
          selectedRow.original.status = "REJECTED";
          alert(`Appointment successfully rejected`);
        }

        setShowPopup(false);
      })
      .catch((error) => {
        alert(`${error.response.data.message}`);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };
  return (
    <div className="container mx-auto flex h-max py-24 gap-24">
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
                    {/* {header.isPlaceholder ? null : (
                      <div
                        {...{
                          className: header.column.getCanSort()
                            ? "cursor-pointer select-none flex min-w-[36px]"
                            : "",
                          onClick: header.column.getToggleSortingHandler(),
                        }}
                      > */}
                        {flexRender(
                          header.column.columnDef.header,
                          header.getContext()
                        )}
                        {/* {{
                          asc: <span className="pl-2">↑</span>,
                          desc: <span className="pl-2">↓</span>,
                        }[header.column.getIsSorted()] ?? null}
                      </div>
                    )} */}
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

        <div className="w-full mt-8">
          <div className="flex gap-2 ">
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
          <div className="flex justify-center w-full">
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
      {showPopup && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            {/* Popup content */}
            <h2 className="text-xl font-semibold mb-4">Insert Notes</h2>
            <textarea
              className="w-full h-32 border rounded-md p-2 mb-4"
              placeholder="Insert notes here..."
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
            ></textarea>
            <div className="flex justify-end">
              <button
                className="bg-blue-500 text-white font-semibold px-6 py-3 rounded-md mr-4"
                onClick={() => handlePopupClose()}
              >
                Close
              </button>
              <button
                className="bg-green-500 text-white font-semibold px-6 py-3 rounded-md"
                onClick={() => handleSubmitClick()}
                disabled={isLoading}
              >
                {isLoading ? (
                  <div className="flex items-center">
                    <span className="mr-2">Submitting...</span>
                    <svg
                      className="animate-spin h-5 w-5 text-white"
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                    >
                      <circle
                        className="opacity-25"
                        cx="12"
                        cy="12"
                        r="10"
                        stroke="currentColor"
                        strokeWidth="4"
                      ></circle>
                      <path
                        className="opacity-75"
                        fill="currentColor"
                        d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                      ></path>
                    </svg>
                  </div>
                ) : (
                  "Submit"
                )}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
