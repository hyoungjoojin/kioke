import { GetJournalResponse } from "@/types/api/journal";
import { NextRequest, NextResponse } from "next/server";

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> }
): Promise<NextResponse<GetJournalResponse>> {
  const id = (await params).id;

  return NextResponse.json(
    {
      id: id,
      title: id,
    },
    {
      status: 200,
    }
  );
}
