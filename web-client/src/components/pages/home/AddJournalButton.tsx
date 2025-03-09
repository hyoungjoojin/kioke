"use client";

import { Button } from "@/components/ui/button";
import { useShelvesQuery } from "@/hooks/query/shelf";
import { useSelectedShelf } from "@/hooks/store";
import { SquarePen } from "lucide-react";
import Link from "next/link";

export default function AddJournalButton() {
  const { data: shelves } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(shelves);

  if (selectedShelf?.isArchive) {
    return null;
  }

  return (
    <Link href="/journal/new">
      <Button variant="outline">
        <SquarePen />
      </Button>
    </Link>
  );
}
