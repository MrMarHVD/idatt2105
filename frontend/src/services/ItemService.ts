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
    const response = await api.get<ItemPreviewType[]>('/items/all'); //
    console.log("url: " + api.defaults.baseURL + "/items/all");
    console.log("Data:" + response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching items:', error);
    throw error;
  }
}

// Existing function to fetch details for a single item
export async function fetchItem(itemId: string | number): Promise<ItemDetailsType> {
  try {
    // NOTE: This endpoint likely needs updating for your actual API
    // Using relative path and assuming endpoint like /items/{itemId} or similar
    const response = await api.get<ItemDetailsType>(`/items/details/${itemId}`);
    console.log(response.data, "here is data for item", itemId); // Log fetched data
    return response.data;
  } catch (error) {
    console.error(`Error fetching item with ID ${itemId}:`, error);
    throw error;
  }
}

export async function fetchPreviewItemsByCategoryId(categoryId: string): Promise<ItemPreviewType[]> {
  try {
    const response = await api.get<ItemPreviewType[]>(`/items/category/${categoryId}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching items for category ID ${categoryId}:`, error);
    throw error;
  }
}

/**
 * Records that a user viewed an item.
 * The user is automatically identified through the authentication token.
 *
 * @param itemId - The ID of the viewed item
 * @returns Promise that resolves when the view has been recorded
 */
export async function recordItemView(itemId: string | number): Promise<void> {
  try {
    // Send the item ID to the backend
    // The user ID will be extracted from the JWT token by the backend
    await api.post(`/items/view/post/${itemId}`);
    console.log(`View recorded for item ${itemId}`);
  } catch (error) {
    // Log the error but don't throw it to avoid disrupting the user experience
    console.error(`Failed to record view for item ${itemId}:`, error);
  }
}

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

