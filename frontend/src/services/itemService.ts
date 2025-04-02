import api from '@/services/api/axiosInstance'; // [cite: uploaded:src/services/api/axiosInstance.ts]
import type { ItemPreviewType, ItemDetailsType } from '@/models/Item'; // [cite: uploaded:src/models/Item.ts] Adjusted import

// Define an interface for the expected paginated response (needed by fetchFavoriteItems)
export interface PaginatedItemsResponse {
  items: ItemPreviewType[];
  totalItems: number;
  // Add other fields if your API provides them (e.g., totalPages, currentPage)
}

// Existing function to fetch all preview items (potentially needs pagination update too)
export async function fetchPreviewItems(): Promise<ItemPreviewType[]> {
  try {
    // NOTE: This endpoint likely needs updating for your actual API or pagination
    const response = await api.get<ItemPreviewType[]>('/items/all'); // Using relative path assuming '/api' base
    console.log("item preview data", response.data); // Log fetched data TODO: remove
    return response.data;
  } catch (error) {
    console.error('Error fetching preview items:', error);
    throw error;
  }
}

// Existing function to fetch details for a single item
export async function fetchItem(itemId: string | number): Promise<ItemDetailsType> {
  try {
    // NOTE: This endpoint likely needs updating for your actual API
    // Using relative path and assuming endpoint like /items/{itemId} or similar
    const response = await api.get<ItemDetailsType>(`/items/${itemId}`);
    console.log(response.data, "here is data for item", itemId); // Log fetched data
    return response.data;
  } catch (error) {
    console.error(`Error fetching item with ID ${itemId}:`, error);
    throw error;
  }
}

// --- ADDED FUNCTION FOR FAVORITES (with pagination) ---
/**
 * Fetches a paginated list of favorite items for the logged-in user.
 * Assumes the backend endpoint /api/users/me/favorites supports pagination.
 * @param page - The page number to fetch (e.g., 1-based)
 * @param limit - The number of items per page
 */
export async function fetchFavoriteItems(page: number, limit: number): Promise<PaginatedItemsResponse> {
  try {
    // Uses the base URL and auth token from the shared axios instance 'api'.
    // Assumes endpoint is relative to baseURL (e.g., /api/users/me/favorites)
    const response = await api.get<PaginatedItemsResponse>('/users/me/favorites', {
      params: { // Send parameters as query string ?page=...&limit=...
        page: page,
        limit: limit
      }
    });
    return response.data;
  } catch (error) {
    console.error(`Error fetching favorite items (page ${page}, limit ${limit}):`, error);
    throw error;
  }
}
// --- END ADDED FUNCTION ---
