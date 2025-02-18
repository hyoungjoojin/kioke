"use client";

import { TableCell, TableRow } from "@/components/ui/table";
import { useShelf } from "@/hooks/store";

export default function JournalList() {
  const { selectedShelf } = useShelf();

  return (
    <>
      {selectedShelf?.journals.map((journal, index) => {
        return (
          <TableRow key={index}>
            <TableCell>{journal.title}</TableCell>
            <TableCell>{journal.id}</TableCell>
          </TableRow>
        );
      })}
    </>
  );
}
