"use client";

import { useShelf } from "@/hooks/store";
import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { useRouter } from "next/navigation";

interface JournalRowProps {
  id: string;
  title: string;
}

const JournalRow = (props: JournalRowProps) => {
  const router = useRouter();

  const { title, id } = props;

  return (
    <TableRow
      className="hover:bg-muted hover:cursor-pointer"
      onClick={() => {
        router.push(`/journal/${id}/preview`);
      }}
    >
      <TableCell>{title}</TableCell>
      <TableCell>{id}</TableCell>
    </TableRow>
  );
};

export default function JournalList() {
  const { selectedShelf } = useShelf();

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Title</TableHead>
          <TableHead>Description</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {selectedShelf?.journals.map((journal, index) => {
          return (
            <JournalRow key={index} id={journal.id} title={journal.title} />
          );
        })}
      </TableBody>
    </Table>
  );
}
