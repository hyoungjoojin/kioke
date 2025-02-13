"use client";

import { useBoundStore } from "@/components/providers/StoreProvider";
import { TableCell, TableRow } from "@/components/ui/table";

export default function JournalList() {
  const selectedShelf = useBoundStore((state) =>
    state.actions.getSelectedShelf(),
  );

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
